#import "QuikklyManager.h"
#import "QuikklyScanViewManager.h"

@interface QuikklyScanView : UIView <QKScanViewDelegate>
{
    @private
    QKScanView *m_scanView;
}

@property (nonatomic, copy) RCTBubblingEventBlock onScanCode;

@end

@implementation QuikklyScanView

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    
    if(self) {
        [QuikklyManager configure];
        
        m_scanView = [[QKScanView alloc] initWithFrame:frame];
        m_scanView.delegate = self;
        
        [self addSubview:m_scanView];
    }
    
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    
    m_scanView.frame = self.bounds;
}

- (void)didMoveToWindow {
    [super didMoveToWindow];
    
    if(self.window) {
        [m_scanView start];
    } else {
        [m_scanView stop];
    }
}

- (void)scanView:(QKScanView *)scanView didDetectScannables:(NSArray<QKScannable *> *)scannables {
    if(self.onScanCode) {
        QKScannable *scannable = scannables.firstObject;
        
        if(scannable) {
            NSMutableDictionary *result = [[NSMutableDictionary alloc] init];
            
            [result setObject:[NSNumber numberWithUnsignedLongLong:scannable.value].stringValue forKey:@"value"];
            self.onScanCode(result);
        }
    }
}

@end

@implementation QuikklyScanViewManager

RCT_EXPORT_MODULE();

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

RCT_EXPORT_VIEW_PROPERTY(onScanCode, RCTBubblingEventBlock)

- (UIView *)view {
    return [[QuikklyScanView alloc] initWithFrame:CGRectZero];
}

@end
