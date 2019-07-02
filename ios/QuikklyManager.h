#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <Quikkly/Quikkly.h>

extern NSString *QuikklyManagerErrorDomain;

@interface QuikklyManager : NSObject <RCTBridgeModule>

+ (void)configure;

@end
