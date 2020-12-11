#include <string>
#include <queue>
#include <list>

using namespace std;

typedef struct Process Process;
struct Process {
	int arrival;
	int first_run;
	int duration;
	int completion;
};

class ArrivalComparator {
public:
	bool operator()(const Process lhs, const Process rhs)  const {
		if (lhs.arrival != rhs.arrival)
			return lhs.arrival > rhs.arrival;
		else
			return lhs.duration > rhs.duration;    
	}
};

class DurationComparator {
public:
	bool operator()(const Process lhs, const Process rhs)  const {
		if (lhs.duration != rhs.duration)
			return lhs.duration > rhs.duration;
		else
			return lhs.arrival > rhs.arrival;
	}
};

class CompletionComparator {
public:
	bool operator()(const pair<Process, int> lhs, const pair<Process, int> rhs)  const {
		return lhs.first.duration - lhs.second > rhs.first.duration - rhs.second;
	}
};

typedef priority_queue<Process, vector<Process>, ArrivalComparator> pqueue_arrival;
typedef priority_queue<Process, vector<Process>, DurationComparator> pqueue_duration;
typedef priority_queue<pair<Process, int>, vector<pair<Process, int>>, CompletionComparator> pqueue_completion;

pqueue_arrival read_workload(string filename);
void show_workload(pqueue_arrival workload);
void show_processes(list<Process> processes);

list<Process> fifo(pqueue_arrival workload);
list<Process> sjf(pqueue_arrival workload);
list<Process> stcf(pqueue_arrival workload);
list<Process> rr(pqueue_arrival workload);

float avg_turnaround(list<Process> processes);
float avg_response(list<Process> processes);
void show_metrics(list<Process> processes);
