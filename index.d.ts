export as namespace FirstLibrary;

export interface EventTimeRange {
  startDate: number;
  endDate: number;
}

export interface Event {
  id: string;
  title: string;
}

export function getEvents(timeRange: EventTimeRange): Promise<Event[]>;
export function saveEvent(title: string, timeRange: EventTimeRange): Promise<void>;
export function deleteEvent(eventId: string): Promise<void>;
