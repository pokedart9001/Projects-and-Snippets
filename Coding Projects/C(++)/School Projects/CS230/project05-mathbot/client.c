#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

int check_socket(int domain, int type, int protocol) {
	int socket_desc = socket(domain, type, protocol);
	if (socket_desc < 0) {
		perror("socket create failure");
		exit(EXIT_FAILURE);
	}
	return socket_desc;
}

ssize_t check_connect(int socket, const struct sockaddr *address, socklen_t address_len) {
	ssize_t out = connect(socket, address, address_len);
	if (out < 0) {
		perror("socket connect failure");
		exit(EXIT_FAILURE);
	}
	return out;
}

ssize_t check_send(int socket, const void * buffer, size_t length, int flags) {
	ssize_t out = send(socket, buffer, length, flags);
	if (out < 0) {
		perror("message send failure");
		exit(EXIT_FAILURE);
	}
	return out;
}

ssize_t check_recv(int socket, void * buffer, size_t length, int flags) {
	ssize_t out = recv(socket, buffer, length, flags);
	if (out < 0) {
		perror("initial message send failure");
		exit(EXIT_FAILURE);
	}
}

int solve(char *exp) {
	int num1 = atoi(strtok(exp, " "));
	char op = *(strtok(NULL, " "));
	int num2 = atoi(strtok(NULL, "\n"));
	switch (op) {
		case '+': return num1 + num2;
		case '-': return num1 - num2;
		case '*': return num1 * num2;
		case '/': return num2 == 0 ? 0 : num1 / num2;
		default: return 0;
	}
}

int randInt(int lower, int upper) {
	return rand() % (upper - lower + 1) + lower;
}

int main(int argc, char *argv[]) {
	char *net_id = argv[1];
	int port = atoi(argv[2]);
	char *address = argv[3];

	int socket_desc = check_socket(AF_INET, SOCK_STREAM, 0);

	struct sockaddr_in server = {
		.sin_family = AF_INET,
		.sin_port = htons(port),
		.sin_addr = { .s_addr = inet_addr(address) }
	};

	check_connect(socket_desc, (struct sockaddr *)&server, sizeof(server));

	char begin_message[50];
	sprintf(begin_message, "cs230 HELLO %s\n", net_id);
	check_send(socket_desc, begin_message, strlen(begin_message), 0);
	printf("send: %s", begin_message);

	while (1) {
		char response[100];
		check_recv(socket_desc, response, 100, 0);
		printf("recieve: %s", response);
		if (strncmp(response + 6, "STATUS\n", 6) == 0) {
			char answer_message[50];
			sprintf(answer_message, "cs230 %i\n", solve(response + 13));
			check_send(socket_desc, answer_message, strlen(answer_message), 0);
			printf("send: %s", answer_message);
		} else {
			close(socket_desc);
			break;
		}
	}

	return 0;
}