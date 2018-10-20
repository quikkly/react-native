#import <React/RCTLog.h>
#import "QuikklyManager.h"
#import "QuikklyScanViewController.h"

#define QUIKKLY_KEY_VALUE @"value"
#define QUIKKLY_KEY_TEMPLATE @"template"
#define QUIKKLY_KEY_SKIN @"skin"
#define QUIKKLY_KEY_BACKGROUND_COLOR @"backgroundColor"
#define QUIKKLY_KEY_BORDER_COLOR @"borderColor"
#define QUIKKLY_KEY_DATA_COLOR @"dataColor"
#define QUIKKLY_KEY_MASK_COLOR @"maskColor"
#define QUIKKLY_KEY_OVERLAY_COLOR @"overlayColor"
#define QUIKKLY_KEY_IMAGE_FILE @"imageFile"

NSString *QuikklyManagerErrorDomain = @"QuikklyError";

static void QuikklyInitialize() {
    static dispatch_once_t onceToken;
    
    dispatch_once(&onceToken, ^{
        QKQuikkly.apiKey = @"unused";
    });
}

static NSString *QuikklyGetStringOption(NSDictionary *options, NSString *key) {
    NSObject *value = [options objectForKey:key];
    
    return ([value isKindOfClass:[NSString class]]) ? (NSString *)value : nil;
}

@implementation QuikklyManager

RCT_EXPORT_MODULE();

- (NSDictionary *)constantsToExport {
    return @{ @"VERSION": [NSString stringWithUTF8String:QC_VERSION_STR] };
}

RCT_EXPORT_METHOD(createImage:(NSDictionary *)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    NSObject *value = [options objectForKey:QUIKKLY_KEY_VALUE];
    NSObject *template = [options objectForKey:QUIKKLY_KEY_TEMPLATE];
    NSDictionary *skinOptions = [options objectForKey:QUIKKLY_KEY_SKIN];
    QKScannableSkin *skin = [[QKScannableSkin alloc] init];
    NSString *result;
    
    QuikklyInitialize();
    
    if([value isKindOfClass:[NSString class]]) {
        value = [NSNumber numberWithLongLong:((NSString *)value).longLongValue];
    }
    
    if(![value isKindOfClass:[NSNumber class]]) {
        RCTLogError(@"'%@' must be a number %@", QUIKKLY_KEY_VALUE, value.class);
        value = nil;
    }
    
    if(template && ![template isKindOfClass:[NSString class]]) {
        RCTLogError(@"'%@' must be a string", QUIKKLY_KEY_TEMPLATE);
        template = nil;
    }
    
    if(skinOptions && ![skinOptions isKindOfClass:[NSDictionary class]]) {
        RCTLogError(@"'%@' must be an object", QUIKKLY_KEY_SKIN);
        skinOptions = nil;
    }
    
    if(skinOptions) {
        NSString *parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_BACKGROUND_COLOR);
        
        if(parameter) {
            skin.backgroundColor = parameter;
        }
        
        parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_BORDER_COLOR);
        
        if(parameter) {
            skin.borderColor = parameter;
        }
        
        parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_DATA_COLOR);
        
        if(parameter) {
            skin.dataColors = @[ parameter ];
        }
        
        parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_MASK_COLOR);
        
        if(parameter) {
            skin.maskColor = parameter;
        }
        
        parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_OVERLAY_COLOR);
        
        if(parameter) {
            skin.overlayColor = parameter;
        }
        
        parameter = QuikklyGetStringOption(skinOptions, QUIKKLY_KEY_IMAGE_FILE);
        
        if(parameter) {
            skin.imageUri = parameter;
        }
    }
    
    result = [[QKScannable alloc] initWithValue:((NSNumber *)value).unsignedLongLongValue
                                       template:(NSString *)template
                                           skin:skin].svgString;
    
    if(result) {
        resolve(result);
    } else {
        reject(@"QuikklyUnknown",
               @"Unable to get svg string",
               [NSError errorWithDomain:QuikklyManagerErrorDomain code:-1 userInfo:nil]);
    }
}

RCT_EXPORT_METHOD(scanForResult:(NSDictionary *)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejecter:(RCTPromiseRejectBlock)reject) {
    QuikklyInitialize();
    
    dispatch_async(dispatch_get_main_queue(), ^ {
        UIViewController *rootController = [UIApplication sharedApplication].delegate.window.rootViewController;
        QuikklyScanViewController *scanController;
        BOOL animated = YES;
        
        if(!rootController) {
            reject(@"QuikklyNoContext",
                   @"Unable to get view controller",
                   [NSError errorWithDomain:QuikklyManagerErrorDomain code:-1 userInfo:nil]);
            return;
        }
        
        scanController = [[QuikklyScanViewController alloc] initWithResolver:resolve rejecter:reject];
        
        if(options) {
            NSObject *anim = [options objectForKey:@"animated"];
            NSObject *title = [options objectForKey:@"title"];
            
            if([anim isKindOfClass:[NSNumber class]]) {
                animated = ((NSNumber *)anim).boolValue;
            }
            
            if([title isKindOfClass:[NSString class]]) {
                scanController.title = (NSString *)title;
            }
        }
        
        [rootController presentViewController:scanController animated:animated completion:nil];
    });
}

@end
