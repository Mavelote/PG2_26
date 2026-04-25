CC = gcc

CFLAGS = -Wall -Wextra -g

SRCS = prog25.c \
       book.c \
       processFile.c \
       splitFields.c \
       separatorUnify.c \
       strcmp_ic.c \
       collSortTitle.c \
       collSortRefIsbn.c

OBJS = $(SRCS:.c=.o)

EXEC = prog25

all: $(EXEC)

$(EXEC): $(OBJS)
	$(CC) $(CFLAGS) -o $(EXEC) $(OBJS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) $(EXEC)