#import "FirstLibrary.h"
#import <React/RCTLog.h>
#import <React/RCTConvert.h>
#import <EventKit/EventKit.h>

typedef struct {
  double startDate;
  double endDate;
} GetEventsOptions;

@implementation RCTConvert (GetEventsOptions)
+ (GetEventsOptions)GetEventsOptions:(id)json
{
  NSDictionary<NSString *, id> *options = [RCTConvert NSDictionary:json];

  return (GetEventsOptions){
    .startDate = [RCTConvert NSTimeInterval: options[@"startDate"]],
    .endDate = [RCTConvert NSTimeInterval: options[@"endDate"]],
  };
}

@end

@implementation FirstLibrary

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(saveEvent:(NSString *)title options:(GetEventsOptions)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    EKEventStore *store = [EKEventStore new];
    [store requestAccessToEntityType:EKEntityTypeEvent completion:^(BOOL granted, NSError *error) {
        if (!granted) { return; }
        EKEvent *event = [EKEvent eventWithEventStore:store];
        event.title = title;
        event.startDate = [NSDate dateWithTimeIntervalSince1970:options.startDate];
        event.endDate = [NSDate dateWithTimeIntervalSince1970:options.endDate];
        event.calendar = [store defaultCalendarForNewEvents];
        NSError *err = nil;
        [store saveEvent:event span:EKSpanThisEvent commit:YES error:&err];

        resolve([NSString stringWithFormat:@"%d",event.eventIdentifier]);
    }];
  
}

RCT_EXPORT_METHOD(deleteEvent:(NSString *)id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    EKEventStore* store = [EKEventStore new];
    [store requestAccessToEntityType:EKEntityTypeEvent completion:^(BOOL granted, NSError *error) {
        if (!granted) { return; }
        EKEvent* eventToRemove = [store eventWithIdentifier:id];
        if (eventToRemove) {
            NSError* error = nil;
            [store removeEvent:eventToRemove span:EKSpanThisEvent commit:YES error:&error];
        }
        resolve(@"done");
    }];
  
}

RCT_EXPORT_METHOD(getEvents:(GetEventsOptions)options resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    EKEventStore *store = [[EKEventStore alloc] init];
[store requestAccessToEntityType:EKEntityTypeEvent completion:^(BOOL granted, NSError *error) {
    if(granted) {
        NSMutableArray *list = [NSMutableArray arrayWithObjects: nil];

        NSPredicate *fetchCalendarEvents = [store predicateForEventsWithStartDate:[NSDate dateWithTimeIntervalSince1970:options.startDate] endDate:[NSDate dateWithTimeIntervalSince1970:options.endDate] calendars:nil];
        [store enumerateEventsMatchingPredicate:fetchCalendarEvents usingBlock:^(EKEvent *event, BOOL *stop) {
        [list addObject:@{@"id": event.eventIdentifier, @"title": event.title}];    

        }];
        resolve(list);
    }
}];
  
}

@end
