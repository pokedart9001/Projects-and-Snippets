#include "challenge.h"

// Creates a Time struct given the hour, minute, and second
//   param hour: int representing the hour 0-23
//   param min: int representing the minute 0-59
//   param sec: int representing the second 0-59
//   return: a Time struct for the time representing the same time as the integers
// TODO: complete the function
struct Time create_time(int hour, int min, int sec)
{
	struct Time t = {hour, min, sec};
	return t;
}

// Gets the hour from a Time struct
//   param t: struct representing a time
//   return: an int representing the hour of time t
// TODO: complete the function
int get_hour(struct Time t)
{
	return (t.hour + 24) % 24;
}

// Gets the minute from a Time struct
//   param t: struct representing a time
//   return: an int representing the minute of time t
// TODO: complete the function
int get_min(struct Time t)
{
	return (t.min + 60) % 60;
}

// Gets the second from a Time struct
//   param t: struct representing a time
//   return: an int representing the second of time t
// TODO: complete the function
int get_sec(struct Time t)
{
	return (t.sec + 60) % 60;
}

// TODO: complete the function
void set_hour(struct Time* t, int hour)
{
	t -> hour = hour;
}

// TODO: complete the function
void set_min(struct Time* t, int min)
{
	t -> min = min;
}

// TODO: complete the function
void set_sec(struct Time* t, int sec)
{
	t -> sec = sec;
}

// Creates a Time struct representing the difference between two times
//   param t1: Time struct representing the beginning of interval
//   param t2: Time struct representing the end of interval
//   return: Time struct representing time between t1 and t2
// TODO: complete the function
struct Time elapsed_time(struct Time t1, struct Time t2)
{
	int hour = t2.hour - t1.hour;
	int min = t2.min - t1.min;
	int sec = t2.sec - t1.sec;

	if (sec < 0) {
		sec += 60;
		min -= 1;
	}

	if (min < 0) {
		min += 60;
		hour -= 1;
	}

	if (hour < 0) {
		hour += 24;
	}

	return create_time(hour, min, sec);
}
