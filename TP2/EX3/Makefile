CC = gcc
CFLAGS = -Wall -Wextra -g


SRCS = prog23.c book.c processFile.c splitFields.c separatorUnify.c
OBJS = $(SRCS:.c=.o)
EXEC = prog23

all: $(EXEC)

$(EXEC): $(OBJS)
	$(CC) $(CFLAGS) -o $(EXEC) $(OBJS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) $(EXEC)