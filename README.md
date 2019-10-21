# react-native-quikkly

## What is this repository for

A React Native plugin for the Quikkly iOS and Android SDKs.

## How do I get set up

### React Native

Add Quikkly as an additional dependency and generate native android and/or iOS projects.
Quikkly won't work with [Expo](https://expo.io/) toolchain. See [example](example) how the structure should look like.

```bash
# create a new project
react-native init example
cd example

# a) npm
npm install react-native-quikkly --save

# b) yarn
yarn add react-native-quikkly

# (Mostly) automatic installation
react-native link react-native-quikkly
```

### iOS

Create a Podfile and run it (```pod install```)

```ruby
source 'https://github.com/CocoaPods/Specs.git'
platform :ios, '8.0'

package = JSON.parse(File.read(File.join(__dir__, '../node_modules/react-native-quikkly/package.json')))
version = package['version']

target 'example' do
    use_frameworks!

    pod 'Quikkly', :git => 'https://github.com/quikkly/ios-sdk.git', :tag => "#{version}"
end
```

Add NSCameraUsageDescription to your Info.plist and/or InfoPlist.strings

```xml
<key>NSCameraUsageDescription</key>
<string>The camera is required for scanning codes</string>
```

Set "Always Embed Swift Standard Libraries to Yes" under project settings. Ensure that "Quikkly.xcodeproj" is under Libraries and libRNQuikkly.a is linked.

### Android

Update settings.gradle file. ```react-native link``` will do this automatically.

```groovy
include ':quikkly-react-native'

project(':quikkly-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-quikkly/android')
```

Update build.gradle files

```groovy
// Add an additional maven repository
maven { url 'https://quikklysdks.bintray.com/quikkly-android-sdk' }

// Add project dependency
implementation project(":quikkly-react-native")
```

Update AndroidManifest.xml

```xml
<!-- <manifest> -->
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" android:required="false" />

<!-- <application> -->
<activity android:name="net.quikkly.android.react.QuikklyScanActivity" />
```

Update MainApplication.java. ```react-native link``` will do this automatically.

```java
protected List<ReactPackage> getPackages() {
    return Arrays.<ReactPackage>asList(
        new MainReactPackage(),
        new QuikklyReactPackage()
    );
}
```

## Usage

```javascript
import { Quikkly, QuikklyView } from "react-native-quikkly"

// Print SDK version
console.log(Quikkly.VERSION)

// Create a SVG image
Quikkly.createImage({
    value: 1234, /* required */
    template: "template0001style1", /* optional */
    skin: { /* optional */
        backgroundColor: "#5cb7a6",
        borderColor: "#ffffff",
        dataColor: "000000",
        maskColor: "#5cb7a6",
        overlayColor: "#ffffff",
        imageFile: "path/to/local/file.png"
    }
}).then((result) => {
	console.log(result)
})

// A) Show scan UI
Quikkly.scanForResult({ } /* options */).then((result) => {
    console.log(result.value)
}).catch((err) => {
    console.log(err)
})

// B) Alternatively show scan overlay
export default class App extends Component<Props> {
  onScanCode(result) {
	console.log(result.value)
  }
  
  render() {
    return (
      <View style={styles.container}>
        <QuikklyView style={styles.quikkly} onScanCode={this.onScanCode} />
        ...
      </View>
    );
  }
}
```
