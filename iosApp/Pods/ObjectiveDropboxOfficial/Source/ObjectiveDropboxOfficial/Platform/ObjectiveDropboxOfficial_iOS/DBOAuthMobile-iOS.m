///
/// Copyright (c) 2016 Dropbox, Inc. All rights reserved.
///

#import "DBOAuthMobile-iOS.h"

#import "DBLoadingStatusDelegate.h"
#import "DBLoadingViewController.h"
#import "DBOAuthManager.h"
#import "DBSDKSystem.h"

#pragma mark - Shared application

static DBMobileSharedApplication *s_mobileSharedApplication;

@implementation DBMobileSharedApplication {
  UIApplication *_sharedApplication;
  __weak UIViewController *_Nullable _controller;
  void (^_openURL)(NSURL *);
}

+ (DBMobileSharedApplication *)mobileSharedApplication {
  return s_mobileSharedApplication;
}

+ (void)setMobileSharedApplication:(DBMobileSharedApplication *)mobileSharedApplication {
  s_mobileSharedApplication = mobileSharedApplication;
}

- (instancetype)initWithSharedApplication:(UIApplication *)sharedApplication
                               controller:(UIViewController *)controller
                                  openURL:(void (^)(NSURL *))openURL {
  self = [super init];
  if (self) {
    // fields saved for app-extension safety
    _sharedApplication = sharedApplication;
    _controller = controller;
    _openURL = openURL;
  }
  return self;
}

- (void)presentErrorMessage:(NSString *)message title:(NSString *)title {
  if (_controller) {
    UIAlertController *alertController =
        [UIAlertController alertControllerWithTitle:title
                                            message:message
                                     preferredStyle:(UIAlertControllerStyle)UIAlertControllerStyleAlert];
    [_controller presentViewController:alertController
                              animated:YES
                            completion:^{
                              [NSException raise:@"FatalError" format:@"%@", message];
                            }];
  }
}

- (void)presentErrorMessageWithHandlers:(NSString *)message
                                  title:(NSString *)title
                         buttonHandlers:(NSDictionary<NSString *, void (^)(void)> *)buttonHandlers {
  UIAlertController *alertController =
      [UIAlertController alertControllerWithTitle:title
                                          message:message
                                   preferredStyle:(UIAlertControllerStyle)UIAlertControllerStyleAlert];

  [alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"Cancel", @"Cancels the current window.")
                                                      style:(UIAlertActionStyle)UIAlertActionStyleCancel
                                                    handler:^(UIAlertAction *action) {
#pragma unused(action)
                                                      void (^handler)(void) = buttonHandlers[@"Cancel"];

                                                      if (handler != nil) {
                                                        handler();
                                                      }
                                                    }]];
  [alertController addAction:[UIAlertAction actionWithTitle:NSLocalizedString(@"Retry", @"Retries the previous action.")
                                                      style:(UIAlertActionStyle)UIAlertActionStyleDefault
                                                    handler:^(UIAlertAction *action) {
#pragma unused(action)
                                                      void (^handler)(void) = buttonHandlers[@"Retry"];

                                                      if (handler != nil) {
                                                        handler();
                                                      }
                                                    }]];

  if (_controller) {
    [_controller presentViewController:alertController
                              animated:YES
                            completion:^{
                            }];
  }
}

- (BOOL)presentPlatformSpecificAuth:(NSURL *)authURL {
  [self presentExternalApp:authURL];
  return YES;
}

- (void)presentAuthChannel:(NSURL *)authURL cancelHandler:(DBOAuthCancelBlock)cancelHandler {
  if (_controller) {
    DBMobileSafariViewController *safariViewController =
        [[DBMobileSafariViewController alloc] initWithUrl:authURL cancelHandler:cancelHandler];
    [_controller presentViewController:safariViewController animated:YES completion:nil];
  }
}

- (void)presentExternalApp:(NSURL *)url {
  _openURL(url);
}

- (BOOL)canPresentExternalApp:(NSURL *)url {
  return [_sharedApplication canOpenURL:url];
}

- (void)dismissAuthController {
  if (_controller != nil) {
    if (_controller.presentedViewController != nil && _controller.presentedViewController.isBeingDismissed == NO &&
        [_controller.presentedViewController isKindOfClass:[DBMobileSafariViewController class]]) {
      [_controller dismissViewControllerAnimated:YES completion:nil];
    }
    _controller = nil;
  }
}

- (void)presentLoading {
  if ([self db_isWebOAuthFlow]) {
    [self db_presentLoadingInWeb];
  } else {
    [self db_presentLoadingInApp];
  }
}

- (void)dismissLoading {
  if ([self db_isWebOAuthFlow]) {
    [self db_dismissLoadingInWeb];
  } else {
    [self db_dismissLoadingInApp];
  }
}

#pragma mark Private helpers

- (BOOL)db_isWebOAuthFlow {
  return [_controller.presentedViewController isKindOfClass:[DBMobileSafariViewController class]];
}

// Web OAuth flow, present the spinner over the MobileSafariViewController.
- (void)db_presentLoadingInWeb {
  UIViewController *vc = _controller.presentedViewController;
  if ([vc isKindOfClass:[DBMobileSafariViewController class]]) {
    [self db_presentLoadingInViewController:vc];
  }
}

- (void)db_dismissLoadingInWeb {
  UIViewController *vc = _controller.presentedViewController;
  if ([vc isKindOfClass:[DBMobileSafariViewController class]]) {
    [self db_dismissLoadingInViewController:vc];
  }
}

// Delegate to app to present loading if delegate is set. Otherwise, present the spinner in the view controller.
- (void)db_presentLoadingInApp {
  if (_loadingStatusDelegate) {
    [_loadingStatusDelegate showLoading];
  } else {
    [self db_presentLoadingInViewController:_controller];
  }
}

// Delegate to app to dismiss loading if delegate is set. Otherwise, dismiss the spinner in the view controller.
- (void)db_dismissLoadingInApp {
  if (_loadingStatusDelegate) {
    [_loadingStatusDelegate dismissLoading];
  } else {
    [self db_dismissLoadingInViewController:_controller];
  }
}

- (void)db_presentLoadingInViewController:(UIViewController *)viewController {
  DBLoadingViewController *loadingVC = [[DBLoadingViewController alloc] init];
  loadingVC.modalPresentationStyle = UIModalPresentationOverFullScreen;
  [viewController presentViewController:loadingVC animated:false completion:nil];
}

- (void)db_dismissLoadingInViewController:(UIViewController *)viewController {
  UIViewController *presentedVC = _controller.presentedViewController;
  if ([presentedVC isKindOfClass:[DBLoadingViewController class]]) {
    [presentedVC dismissViewControllerAnimated:false completion:nil];
  }
}

@end

#pragma mark - Web view controller

@implementation DBMobileSafariViewController {
  DBOAuthCancelBlock _cancelHandler;
}

- (instancetype)initWithUrl:(NSURL *)url cancelHandler:(DBOAuthCancelBlock)cancelHandler {
  if (self = [super initWithURL:url]) {
    _cancelHandler = cancelHandler;
    self.delegate = self;
  }
  return self;
}

- (void)dealloc {
  self.delegate = nil;
}

- (void)safariViewController:(SFSafariViewController *)controller didCompleteInitialLoad:(BOOL)didLoadSuccessfully {
  if (!didLoadSuccessfully) {
    [controller dismissViewControllerAnimated:true completion:nil];
  }
}

- (void)safariViewControllerDidFinish:(SFSafariViewController *)controller {
#pragma unused(controller)
  _cancelHandler();
  [[DBMobileSharedApplication mobileSharedApplication] dismissAuthController];
}

@end
