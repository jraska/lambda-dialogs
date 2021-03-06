# Lambda Dialogs
Use lambda expressions to effectively delegate dialog creation to activity to achieve cleaner dialog code

**Library is not stable yet and API is likely to change**

[![Build Status](https://travis-ci.org/jraska/lambda-dialogs.svg?branch=master)](https://travis-ci.org/jraska/lambda-dialogs)
[![Sample](https://img.shields.io/badge/Download-Sample-blue.svg)](https://drive.google.com/open?id=0B0T1YjC17C-rQ1EyX05mTXNicUk)
[![License](https://img.shields.io/badge/license-Apache%202.0-green.svg) ](https://github.com/jraska/lambda-dialogs/blob/master/LICENSE)
[![Download](https://api.bintray.com/packages/jraska/maven/com.jraska%3Alambda-dialogs/images/download.svg) ](https://bintray.com/jraska/maven/com.jraska%3Alambda-dialogs/_latestVersion)

## Why lambda dialogs?

Driving dialog logic is a pain. If you show dialog, you typically want to react on events like button clicks, 
but where to register listener for it? If you show the dialog in Activity, the dialog disappear on rotation and
if you implement dialog fragment you need to put logic with dependencies into dialog or introduce 
some callback mechanism back to Activity.

Lambda Dialogs use power of method references to delegate dialog functionality back to Activities. All the logic 
remains in Activity and you can set your listeners directly. No custom dialog fragments anymore.

## Usage
 ```java
 // Delegate creation of dialog to activity
 LambdaDialogs.delegate(this)
         .parameter("Parameter title") // Can be any Parcelable or Serializable
         .method(SampleActivity::createDialog)
         .validateEagerly(BuildConfig.DEBUG)
         .show();
 
 
 Dialog createDialog(String title) {
   return new AlertDialog.Builder(this)
       .setTitle(title)
       ... 
       .show();
 }

 
```

### Eager validation
Lambda dialogs are based on method references being serializable for better code. It is too easy to do them wrong. This verifies you do everything all right.

 ```java
 // Application.onCreate().
 // Will validate that methods passed are really serializable in debug mode
  @Override public void onCreate() {
    super.onCreate();
    LambdaDialogs.validateEagerly(BuildConfig.DEBUG);
  }


```


## Download

Grab via Gradle:
```groovy
compile 'com.jraska:lambda-dialogs:0.4.0'
```

You will need to setup [Retrolambda][Retrolambda] for to get real advantage of Lambda Dialogs with method references. 

## License

    Copyright 2016 Josef Raska

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
    
  [Retrolambda]: https://github.com/evant/gradle-retrolambda
