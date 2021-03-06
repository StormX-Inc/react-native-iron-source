
# react-native-ironsource

## Getting started

`$ npm install react-native-ironsource --save`

### Mostly automatic installation

`$ react-native link react-native-ironsource`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-ironsource` and add `RNIronsource.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIronsource.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add `import com.ironsource.RNIronsourcePackage;` to the imports at the top of the file
  - Add `new RNIronsourcePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-ironsource'
  	project(':react-native-ironsource').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-ironsource/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-ironsource')
  	```


## Usage
```javascript
import RNIronsource from 'react-native-ironsource';


RNIronsource.startIronSource(appKey, userId, adType);

appKey, userId, adType in String

adType options:
  Rewarded Video: "rewardedvideo",
  Interstitial Ads: "interstitial",
  Offerwall: "offerwall",
  Banner: "banner"
```
