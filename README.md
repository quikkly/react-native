# react-native-quikkly

## What is this repository for

A React Native plugin for the Quikkly iOS and Android SDKs.

## How do I get set up

### React Native (0.61.5)

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
```
N.B. react-native-quikkly supports automatic linking introduced in
react-native 0.60 so we dont need to manually link

### iOS
```bash
cd ios

# optionally refresh podspecs
pod repo update

# update pods i.e. pull the Quikkly framework
pod install
```

Add NSCameraUsageDescription to your Info.plist and/or InfoPlist.strings

```xml
<key>NSCameraUsageDescription</key>
<string>The camera is required for scanning codes</string>
```

Set "Always Embed Swift Standard Libraries to Yes" under project settings. 

### Android

Update build.gradle files

```groovy
// Add an additional maven repository
maven { url 'https://quikklysdks.bintray.com/quikkly-android-sdk' }

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
## example
There is an example shipped with the SDK, please refer to [Example](https://github.com/quikkly/react-native/tree/master/example).

This example demostrates an overlay scanner with a mask image.