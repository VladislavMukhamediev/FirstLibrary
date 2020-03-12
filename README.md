# react-native-first-library

## Getting started

`$ npm install react-native-first-library --save`

### Android add permission to manifest

`<uses-permission android:name="android.permission.READ_CALENDAR" />`

 `<uses-permission android:name="android.permission.WRITE_CALENDAR" />`

## Usage
```javascript
import FirstLibrary from 'react-native-first-library';

// get all events in time range from 10 days ago to 10 days ahead

FirstLibrary.getEvents({ 
  startDate: new Date().getTime() - 84600000*10, 
  endDate: new Date().getTime() + 84600000*10,
}).then(setEvents);

// create event in timerange with title

FirstLibrary.saveEvent(title, {
  startDate: new Date().getTime() + 3600000,
  endDate: new Date().getTime() + 3960000,
}).then(getEvents);

// delete event by id

FirstLibrary.deleteEvent(eventId).then(getEvents)

```
