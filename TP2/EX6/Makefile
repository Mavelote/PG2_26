
CC = gcc
CFLAGS = -Wall -Wextra -g
TARGET = prog26


SRCS = prog26.c book.c processFile.c splitFields.c separatorUnify.c strcmp_ic.c


OBJS = $(SRCS:.c=.o)


all: $(TARGET)


$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJS)


%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@


clean:
	rm -f $(OBJS) $(TARGET)

run: all
	./$(TARGET) dados.csv