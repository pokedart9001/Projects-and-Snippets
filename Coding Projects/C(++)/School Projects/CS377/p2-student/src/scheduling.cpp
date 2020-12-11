#include <string>
#include <fstream>
#include <iostream>
#include <queue>
#include <list>

#include "scheduling.h"

using namespace std;

pqueue_arrival read_workload(string filename)
{
	pqueue_arrival workload;
	ifstream inputs(filename);
	if (inputs.is_open()) {
		int arr, dur;
		while (inputs >> arr >> dur) {
			Process p;
			p.arrival = arr;
			p.duration = dur;
			workload.push(p);
		}
	}
	return workload;
}

void show_workload(pqueue_arrival workload)
{
	pqueue_arrival xs = workload;
	cout << "Workload:" << endl;
	while (!xs.empty())
	{
		Process p = xs.top();
		cout << '\t' << p.arrival << ' ' << p.duration << endl;
		xs.pop();
	}
}

void show_processes(list<Process> processes)
{
	list<Process> xs = processes;
	cout << "Processes:" << endl;
	while (!xs.empty())
	{
		Process p = xs.front();
		cout << "\tarrival=" << p.arrival << ", duration=" << p.duration
		<< ", first_run=" << p.first_run << ", completion=" << p.completion << endl;
		xs.pop_front();
	}
}

list<Process> run_jobs_in_order(pqueue_arrival job_queue, int *time) {
	list<Process> complete;
	while (!job_queue.empty()) {
		Process p = (Process)job_queue.top();
		job_queue.pop();
		*time = max(*time, p.arrival);
		p.first_run = *time;
		p.completion = p.first_run + p.duration;
		*time = p.completion;
		complete.push_back(p);
	}
	return complete;
}

list<Process> run_jobs_in_order(pqueue_duration *job_queue, int *time) {
	list<Process> complete;
	while (!job_queue->empty()) {
		Process p = (Process)job_queue->top();
		job_queue->pop();
		*time = max(*time, p.arrival);
		p.first_run = *time;
		p.completion = p.first_run + p.duration;
		*time = p.completion;
		complete.push_back(p);
	}
	return complete;
}

list<Process> fifo(pqueue_arrival workload)
{
	int current_time = 0;
	return run_jobs_in_order(workload, &current_time);
}

list<Process> sjf(pqueue_arrival workload)
{
	list<Process> complete;
	pqueue_duration job_order;
	int current_time = 0;
	while (!workload.empty()) {
		Process job = (Process)workload.top();
		workload.pop();
		if (!job_order.empty() && job.arrival > job_order.top().arrival) {
			complete.splice(complete.end(), run_jobs_in_order(&job_order, &current_time));
		}
		job_order.push(job);
	}
	complete.splice(complete.end(), run_jobs_in_order(&job_order, &current_time));
	return complete;
}

list<Process> stcf(pqueue_arrival workload)
{
	list<Process> complete;
	pqueue_completion waiting;
	for (int current_time = 0; !workload.empty() || !waiting.empty(); current_time++) {
		if (!waiting.empty()) {
			pair<Process, int> p = waiting.top();
			waiting.pop();

			if (p.first.first_run == -1) {
				p.first.first_run = current_time - 1;
			}

			p.second++;
			if (p.first.duration == p.second) {
				p.first.completion = current_time;
				complete.push_back(p.first);
			} else {
				waiting.push(p);
			}
		}
		while (!workload.empty() && !(workload.top().arrival > current_time)) {
			Process p = workload.top();
			p.first_run = -1;
			waiting.push(make_pair(p, 0));
			workload.pop();
		}
	}
	return complete;
}

list<Process> rr(pqueue_arrival workload)
{
	list<Process> complete;
	queue<pair<Process, int>> waiting;
	for (int current_time = 0; !workload.empty() || !waiting.empty(); current_time++) {
		if (!waiting.empty()) {
			pair<Process, int> p = waiting.front();
			waiting.pop();

			if (p.first.first_run == -1) {
				p.first.first_run = current_time - 1;
			}

			p.second++;
			if (p.first.duration == p.second) {
				p.first.completion = current_time;
				complete.push_back(p.first);
			} else {
				waiting.push(p);
			}
		}
		while (!workload.empty() && !(workload.top().arrival > current_time)) {
			Process p = workload.top();
			p.first_run = -1;
			waiting.push(make_pair(p, 0));
			workload.pop();
		}
	}
	return complete;
}

float avg_turnaround(list<Process> processes)
{
	float avg = 0.0;
	for (Process p : processes) {
		avg += p.completion - p.arrival;
	}
	avg /= processes.size();
	return avg;
}

float avg_response(list<Process> processes)
{
	float avg = 0.0;
	for (Process p : processes) {
		avg += p.first_run - p.arrival;
	}
	avg /= processes.size();
	return avg;
}

void show_metrics(list<Process> processes)
{
	show_processes(processes);
	cout << '\n';
	cout << "# of Processes: " << processes.size() << endl;
	cout << "Average Turnaround Time: " << avg_turnaround(processes) << endl;
	cout << "Average Response Time:   " << avg_response(processes) << endl;
}