///
/// Copyright (c) 2016 Dropbox, Inc. All rights reserved.
///

#import <UIKit/UIKit.h>

#import "DBClientsManager+Protected.h"
#import "DBClientsManager.h"
#import "DBLoadingStatusDelegate.h"
#import "DBOAuthManager.h"
#import "DBOAuthMobile-iOS.h"
#import "DBOAuthMobileManager-iOS.h"
#import "DBScopeRequest.h"
#import "DBTransportDefaultConfig.h"

@implementation DBClientsManager (MobileAuth)

+ (void)authorizeFromController:(UIApplication *)sharedApplication
                     controller:(UIViewController *)controller
                        openURL:(void (^_Nonnull)(NSURL *))openURL {
  [self db_authorizeFromController:sharedApplication
                        controller:controller
             loadingStatusDelegate:nil
                           openURL:openURL
                           usePkce:NO
                      scopeRequest:nil];
}

+ (void)authorizeFromControllerV2:(UIApplication *)sharedApplication
                       controller:(nullable UIViewController *)controller
            loadingStatusDelegate:(nullable id<DBLoadingStatusDelegate>)loadingStatusDelegate
                          openURL:(void (^_Nonnull)(NSURL *))openURL
                     scopeRequest:(nullable DBScopeRequest *)scopeRequest {
  [self db_authorizeFromController:sharedApplication
                        controller:controller
             loadingStatusDelegate:loadingStatusDelegate
                           openURL:openURL
                           usePkce:YES
                      scopeRequest:scopeRequest];
}

+ (void)setupWithAppKey:(NSString *)appKey {
  [[self class] setupWithTransportConfig:[[DBTransportDefaultConfig alloc] initWithAppKey:appKey]];
}

+ (void)setupWithTransportConfig:(DBTransportDefaultConfig *)transportConfig {
  [[self class] setupWithOAuthManager:[[DBOAuthMobileManager alloc] initWithAppKey:transportConfig.appKey
                                                                              host:transportConfig.hostnameConfig.meta
                                                                       redirectURL:transportConfig.redirectURL]
                      transportConfig:transportConfig];
}

+ (void)setupWithTeamAppKey:(NSString *)appKey {
  [[self class] setupWithTeamTransportConfig:[[DBTransportDefaultConfig alloc] initWithAppKey:appKey]];
}

+ (void)setupWithTeamTransportConfig:(DBTransportDefaultConfig *)transportConfig {
  [[self class]
      setupWithOAuthManagerTeam:[[DBOAuthMobileManager alloc] initWithAppKey:transportConfig.appKey
                                                                        host:transportConfig.hostnameConfig.meta
                                                                 redirectURL:transportConfig.redirectURL]
                transportConfig:transportConfig];
}

#pragma - mark Private methods

+ (void)db_authorizeFromController:(UIApplication *)sharedApplication
                        controller:(nullable UIViewController *)controller
             loadingStatusDelegate:(nullable id<DBLoadingStatusDelegate>)loadingStatusDelegate
                           openURL:(void (^_Nonnull)(NSURL *))openURL
                           usePkce:(BOOL)usePkce
                      scopeRequest:(nullable DBScopeRequest *)scopeRequest {
  NSAssert([DBOAuthManager sharedOAuthManager] != nil,
           @"Call `Dropbox.setupWithAppKey` or `Dropbox.setupWithTeamAppKey` before calling this method");
  DBMobileSharedApplication *sharedMobileApplication =
      [[DBMobileSharedApplication alloc] initWithSharedApplication:sharedApplication
                                                        controller:controller
                                                           openURL:openURL];
  sharedMobileApplication.loadingStatusDelegate = loadingStatusDelegate;
  [DBMobileSharedApplication setMobileSharedApplication:sharedMobileApplication];
  [[DBOAuthManager sharedOAuthManager] authorizeFromSharedApplication:sharedMobileApplication
                                                              usePkce:usePkce
                                                         scopeRequest:scopeRequest];
}

@end
