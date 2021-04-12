# SpaceX Times
###### [![build](https://github.com/alexmaryin/spacextimes/actions/workflows/build.yml/badge.svg)](https://github.com/alexmaryin/spacextimes/actions/workflows/build.yml)  *beta version is already to play*

Android application for fetching all information about SpaceX&trade; rocket's launches and landings, posted media, vehicles technical data, crew members and over more. 

Application runs on [r/spaceX-API](https://github.com/r-spacex/SpaceX-API) engine version 4.

**Already launched:**

- [x] Capsules list
- [x] Cores list
- [x] Crew members list, member details
- [x] Dragons list, dragon details
- [x] Rockets list, rocket details
- [x] Launch pads list with google-map location, pad details
- [x] Landing pads list with google-map location, pad details
- [x] Launches list, launches details
- [x] Company milestone events 
- [x] Navigation between elements

**To do:**
- [ ] payloads list, details

**Interface languages:**
- [x] Russian
- [x] English

**Features:**
* Auto-translation for english descriptions and remarks by [FastTranslator](https://fasttranslator.herokuapp.com/)
* Wiki-pages interlanguage auto-selecting with [Jsoup](https://jsoup.org/)
* Cache for translations on Room (sqlite)
* Cache for network responses and pictures download by okHttp and Picasso
* Memory cache for fetched data in application life

**UI previews -- ARE STILL SUBJECT TO CHANGE!!!!**

**English**

![screen 1](/readme_images/screenshot_1.png)
![screen 2](/readme_images/screenshot_2.png)
![screen 3](/readme_images/screenshot_3.png)
![screen 4](/readme_images/screenshot_4.png)


**Russian**

![screen 5](/readme_images/screenshot_5.png)
![screen 6](/readme_images/screenshot_6.png)
![screen 7](/readme_images/screenshot_7.png)
![screen 8](/readme_images/screenshot_8.png)

**License**
```
Copyright 2021 Alex Maryin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
