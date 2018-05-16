#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>
#import <Quikkly/Quikkly.h>

@interface QuikklyScanViewController : QKScanViewController {
    @private
    RCTPromiseResolveBlock m_resolve;
    RCTPromiseRejectBlock m_reject;
}

- (id)initWithResolver:(RCTPromiseResolveBlock)resolve
              rejecter:(RCTPromiseRejectBlock)reject;

@end
