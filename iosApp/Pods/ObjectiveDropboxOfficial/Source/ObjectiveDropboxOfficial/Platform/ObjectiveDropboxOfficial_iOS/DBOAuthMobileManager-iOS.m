///
/// Copyright (c) 2016 Dropbox, Inc. All rights reserved.
///

#import "DBOAuthMobileManager-iOS.h"

#import "DBLoadingViewController.h"
#import "DBOAuthConstants.h"
#import "DBOAuthManager+Protected.h"
#import "DBOAuthMobile-iOS.h"
#import "DBOAuthPKCESession.h"
#import "DBOAuthResult.h"
#import "DBOAuthUtils.h"
#import "DBScopeRequest+Protected.h"
#import "DBSharedApplicationProtocol.h"

#pragma mark - OAuth manager base (iOS)

static NSString *kDBLinkNonce = @"dropbox.sync.nonce";

@implementation DBOAuthMobileManager {
  NSURL *_dauthRedirectURL;
}

- (instancetype)initWithAppKey:(NSString *)appKey {
  return [self initWithAppKey:appKey host:nil];
}

- (instancetype)initWithAppKey:(NSString *)appKey host:(NSString *)host {
  return [self initWithAppKey:appKey host:host redirectURL:nil];
}

- (instancetype)initWithAppKey:(NSString *)appKey host:(NSString *)host redirectURL:(NSString *)redirectURL {
  self = [super initWithAppKey:appKey host:host redirectURL:redirectURL];
  if (self) {
    _dauthRedirectURL = [NSURL URLWithString:[NSString stringWithFormat:@"db-%@://1/connect", appKey]];
    [_urls addObject:_dauthRedirectURL];
  }
  return self;
}

- (void)extractFromUrl:(NSURL *)url completion:(DBOAuthCompletion)completion {
  if ([url.host isEqualToString:_dauthRedirectURL.host]) { // dauth
    [self extractfromDAuthURL:url completion:completion];
  } else {
    [self extractAuthResultFromRedirectURL:url completion:completion];
  }
}

- (BOOL)checkAndPresentPlatformSpecificAuth:(id<DBSharedApplication>)sharedApplication {
  if (![self hasApplicationQueriesSchemes]) {
    NSString *message = @"DropboxSDK: unable to link; app isn't registered to query for URL schemes dbapi-2 and "
                        @"dbapi-8-emm. In your project's Info.plist file, add a \"dbapi-2\" value and a "
                        @"\"dbapi-8-emm\" value associated with the following keys: \"Information Property List\" > "
                        @"\"LSApplicationQueriesSchemes\" > \"Item <N>\" and \"Item <N+1>\".";
    NSString *title = @"ObjectiveDropbox Error";
    [sharedApplication presentErrorMessage:message title:title];
    return YES;
  }

  NSString *scheme = [self dAuthScheme:sharedApplication];

  if (scheme != nil) {
    NSURL *url = nil;
    if (_authSession) {
      // Code flow
      url = [self dAuthURL:scheme authSession:_authSession];
    } else {
      // Token flow
      NSString *nonce = [[NSUUID alloc] init].UUIDString;
      [[NSUserDefaults standardUserDefaults] setObject:nonce forKey:kDBLinkNonce];
      url = [self dAuthURL:scheme nonce:nonce];
    }
    NSAssert(url, @"Failed to create dauth url.");
    [sharedApplication presentExternalApp:url];
    return YES;
  }

  return NO;
}

- (BOOL)handleRedirectURL:(NSURL *)url completion:(DBOAuthCompletion)completion {
  return [super handleRedirectURL:url
                       completion:^(DBOAuthResult *result) {
                         [[DBMobileSharedApplication mobileSharedApplication] dismissAuthController];
                         completion(result);
                       }];
}

- (NSURL *)dAuthURL:(NSString *)scheme nonce:(NSString *)nonce {
  NSURLComponents *components = [self db_dauthUrlCommonComponentsWithScheme:scheme];
  if (nonce != nil) {
    NSString *state = [NSString stringWithFormat:@"oauth2:%@", nonce];
    components.queryItems =
        [components.queryItems arrayByAddingObject:[NSURLQueryItem queryItemWithName:kDBStateKey value:state]];
  }
  return components.URL;
}

- (NSURL *)dAuthURL:(NSString *)scheme authSession:(DBOAuthPKCESession *)authSession {
  NSURLComponents *components = [self db_dauthUrlCommonComponentsWithScheme:scheme];
  NSString *extraQueryParams = [DBOAuthMobileManager db_createExtraQueryParamsStringForAuthSession:authSession];
  components.queryItems = [components.queryItems arrayByAddingObjectsFromArray:@[
    [NSURLQueryItem queryItemWithName:kDBStateKey value:authSession.state],
    [NSURLQueryItem queryItemWithName:kDBExtraQueryParamsKey value:extraQueryParams],
  ]];
  return components.URL;
}

- (NSString *)dAuthScheme:(id<DBSharedApplication>)sharedApplication {
  if ([sharedApplication canPresentExternalApp:[self dAuthURL:@"dbapi-2" nonce:nil]]) {
    return @"dbapi-2";
  } else if ([sharedApplication canPresentExternalApp:[self dAuthURL:@"dbapi-8-emm" nonce:nil]]) {
    return @"dbapi-8-emm";
  } else {
    return nil;
  }
}

- (void)extractfromDAuthURL:(NSURL *)url completion:(DBOAuthCompletion)completion {
  NSString *path = url.path;
  if (path && [path isEqualToString:@"/connect"]) {
    if (_authSession) {
      // Code flow
      [self db_handleCodeFlowUrl:url authSession:_authSession completion:completion];
    } else {
      // Token flow
      completion([self db_extractFromTokenFlowUrl:url]);
    }
  } else {
    completion(nil);
  }
}

- (BOOL)hasApplicationQueriesSchemes {
  NSArray<NSString *> *queriesSchemes =
      [[NSBundle mainBundle] objectForInfoDictionaryKey:@"LSApplicationQueriesSchemes"];
  BOOL foundApi2 = NO;
  BOOL foundApi8Emm = NO;
  for (NSString *scheme in queriesSchemes) {
    if ([scheme isEqualToString:@"dbapi-2"]) {
      foundApi2 = YES;
    } else if ([scheme isEqualToString:@"dbapi-8-emm"]) {
      foundApi8Emm = YES;
    }
    if (foundApi2 && foundApi8Emm) {
      return YES;
    }
  }
  return NO;
}

- (NSURLComponents *)db_dauthUrlCommonComponentsWithScheme:(NSString *)scheme {
  NSURLComponents *components = [NSURLComponents new];
  components.scheme = scheme;
  components.host = @"1";
  components.path = @"/connect";
  components.queryItems = @[
    [NSURLQueryItem queryItemWithName:@"k" value:_appKey],
    [NSURLQueryItem queryItemWithName:@"s" value:@""],
  ];
  return components;
}

/// Handles code flow response URL from DBApp.
///
/// Auth results are passed back in URL query parameters.
/// Expect results look like below:
///
/// 1. DBApp that can handle dauth code flow properly
/// @code
/// [
///     "state": "<state_string>",
///     "oauth_code": "<oauth_code>"
/// ]
/// @endcode
///
/// 2. Legacy DBApp that calls legacy dauth api, oauth_token should be "oauth2code:" and the code is stored under
/// "oauth_token_secret" key.
/// @code
/// [
///     "state": "<state_string>",
///     "oauth_token": "oauth2code:",
///     "oauth_token_secret": "<oauth_code>"
/// ]
/// @endcode
- (void)db_handleCodeFlowUrl:(NSURL *)url
                 authSession:(DBOAuthPKCESession *)authSession
                  completion:(DBOAuthCompletion)completion {
  NSDictionary<NSString *, NSString *> *parametersMap = [DBOAuthUtils extractDAuthResponseFromUrl:url];
  NSString *state = parametersMap[kDBStateKey];
  if (state == nil || ![state isEqualToString:authSession.state]) {
    completion([DBOAuthResult unknownErrorWithErrorDescription:@"Unable to verify link request."]);
    return;
  }

  NSString *authCode = nil;
  if (parametersMap[kDBOauthCodeKey]) {
    authCode = parametersMap[kDBOauthCodeKey];
  } else if ([parametersMap[kDBOauthTokenKey] isEqualToString:@"oauth2code:"]) {
    authCode = parametersMap[kDBOauthSecretKey];
  }
  if (authCode) {
    [self finishPkceOAuthWithAuthCode:authCode codeVerifier:authSession.pkceData.codeVerifier completion:completion];
  } else {
    completion([DBOAuthResult unknownErrorWithErrorDescription:@"Unable to verify link request."]);
  }
}

/// Handles token flow response URL from DBApp.
///
/// Auth results are passed back in URL query parameters.
/// Expect results look like below:
/// @code
/// [
///     "state": "oauth2:<nonce>",
///     "oauth_token_secret": "<oauth2_access_token>",
///     "uid": "<uid>"
/// ]
/// @endcode
- (DBOAuthResult *)db_extractFromTokenFlowUrl:(NSURL *)url {
  NSDictionary<NSString *, NSString *> *parametersMap = [DBOAuthUtils extractDAuthResponseFromUrl:url];
  NSString *state = parametersMap[kDBStateKey];
  NSString *nonce = (NSString *)[[NSUserDefaults standardUserDefaults] objectForKey:kDBLinkNonce];
  NSString *expectedState = [NSString stringWithFormat:@"oauth2:%@", nonce];
  NSString *accessToken = parametersMap[kDBOauthSecretKey];
  NSString *uid = parametersMap[kDBUidKey];
  if (state && [state isEqualToString:expectedState] && accessToken && uid) {
    return [[DBOAuthResult alloc] initWithSuccess:[DBAccessToken createWithLongLivedAccessToken:accessToken uid:uid]];
  } else {
    return [DBOAuthResult unknownErrorWithErrorDescription:@"Unable to verify link request."];
  }
}

+ (NSString *)db_createExtraQueryParamsStringForAuthSession:(DBOAuthPKCESession *)authSession {
  NSMutableArray<NSString *> *params = [NSMutableArray new];
  NSString *format = @"%@=%@";
  DBPkceData *pkceData = authSession.pkceData;
  [params addObject:[NSString stringWithFormat:format, kDBCodeChallengeKey, pkceData.codeChallenge]];
  [params addObject:[NSString stringWithFormat:format, kDBCodeChallengeMethodKey, pkceData.codeChallengeMethod]];
  [params addObject:[NSString stringWithFormat:format, kDBTokenAccessTypeKey, authSession.tokenAccessType]];
  [params addObject:[NSString stringWithFormat:format, kDBResponseTypeKey, authSession.responseType]];
  DBScopeRequest *scopeRequest = authSession.scopeRequest;
  if (scopeRequest.scopeString) {
    [params addObject:[NSString stringWithFormat:format, kDBScopeKey, scopeRequest.scopeString]];
  }
  if (scopeRequest.includeGrantedScopes) {
    [params addObject:[NSString stringWithFormat:format, kDBIncludeGrantedScopesKey, scopeRequest.scopeType]];
  }
  return [params componentsJoinedByString:@"&"];
}

@end
