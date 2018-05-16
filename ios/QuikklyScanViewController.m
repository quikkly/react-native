#import "QuikklyManager.h"
#import "QuikklyScanViewController.h"

@implementation QuikklyScanViewController

- (id)initWithResolver:(RCTPromiseResolveBlock)resolve
              rejecter:(RCTPromiseRejectBlock)reject {
    self = [super init];
    
    if(self) {
        m_resolve = resolve;
        m_reject = reject;
    }
    
    return self;
}

- (void)scanView:(QKScanView *)scanView didDetectScannables:(NSArray<QKScannable *> *)scannables {
    QKScannable *scannable = scannables.firstObject;
    
    if(scannable) {
        if(m_resolve) {
            NSMutableDictionary *result = [[NSMutableDictionary alloc] init];
            
            m_reject = nil;
            
            [result setObject:[NSNumber numberWithUnsignedLongLong:scannable.value].stringValue forKey:@"value"];
            m_resolve(result);
            m_resolve = nil;
            
            [self dismissVC];
        }
    }
}

- (void)viewDidDisappear:(BOOL)animated {
    [super viewDidDisappear:animated];
    
    if(m_reject) {
        m_reject(@"QuikklyCancelled",
                 @"User cancelled UI",
                 [NSError errorWithDomain:QuikklyManagerErrorDomain code:-2 userInfo:nil]);
        m_reject = nil;
    }
}

@end
