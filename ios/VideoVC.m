
#import "VideoVC.h"
#import <IronSource/IronSource.h>
#import <IronSource/ISConfigurations.h>

@interface VideoVC () <ISOfferwallDelegate>

@end

@implementation VideoVC

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    if (_AdsType == NULL) {
        printf("Crash due to empty adtype");
    }
    if (![IronSource hasOfferwall]) {
        NSMutableDictionary * parameters = [[NSMutableDictionary alloc] init];
        parameters[@"client_session_ip"] = _UserIp;
        parameters[@"session_id"] = _SessionId;
        parameters[@"timestamp"] = _Timestamp;
        [ISConfigurations configurations].offerwallCustomParameters = parameters;

        [IronSource initWithAppKey:_AppKey adUnits:@[_AdsType]];
        [IronSource setOfferwallDelegate:self];
        [IronSource setUserId: _userId];
    } else {
        [IronSource setOfferwallDelegate:self];
        dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
            [IronSource showOfferwallWithViewController:self];
        });
    }
    
    // [ISIntegrationHelper validateIntegration];
    // [IronSource shouldTrackReachability:YES];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)offerwallHasChangedAvailability:(BOOL)available{
    NSLog(@"Offerwall --- availability called");
    
    if (available)
    {
        [IronSource setOfferwallDelegate:self];
        [IronSource showOfferwallWithViewController:self];

    }
}

//Called each time the Offerwall successfully loads for the user
-(void)offerwallDidShow {
    
}

//Called each time the Offerwall fails to show
//@param error - will contain the failure code and description
- (void)offerwallDidFailToShowWithError:(NSError *)error {
    
}

//Called each time the user completes an offer.
//@param creditInfo - A dictionary with the following key-value pairs:
//@"credits" - (integer) The number of credits the user has earned since //the last (void)didReceiveOfferwallCredits:(NSDictionary *)creditInfo event //that returned 'YES'. Note that the credits may represent multiple //completions (see return parameter).
//@"totalCredits" - (integer) The total number of credits ever earned by the
//user.
//@"totalCreditsFlag" - (boolean) In some cases, we won’t be able to //provide the exact amount of credits since the last event(specifically if the user clears the app’s data). In this case the ‘credits’ will be equal to the
//@"totalCredits", and this flag will be @(YES).
//@return The publisher should return a boolean stating if he handled this
//call (notified the user for example). if the return value is 'NO' the
//'credits' value will be added to the next call.
- (BOOL)didReceiveOfferwallCredits:(NSDictionary *)creditInfo {
    return true;
}

// Called when the method ‘-getOWCredits’
//failed to retrieve the users credit balance info.
//@param error - the error object with the failure info
- (void)didFailToReceiveOfferwallCreditsWithError:(NSError *)error {
    
}

//Called when the user closes the Offerwall
-(void)offerwallDidClose {
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(1.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{    
         [self dismissViewControllerAnimated: false completion:nil];
    });
}

@end
