
# react-native-first-library

## Getting started

`$ npm install react-native-first-library --save`

### Mostly automatic installation

`$ react-native link react-native-first-library`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-first-library` and add `RNFirstLibrary.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNFirstLibrary.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNFirstLibraryPackage;` to the imports at the top of the file
  - Add `new RNFirstLibraryPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-first-library'
  	project(':react-native-first-library').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-first-library/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-first-library')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNFirstLibrary.sln` in `node_modules/react-native-first-library/windows/RNFirstLibrary.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using First.Library.RNFirstLibrary;` to the usings at the top of the file
  - Add `new RNFirstLibraryPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNFirstLibrary from 'react-native-first-library';

// TODO: What to do with the module?
RNFirstLibrary;
```
  