#include <stdio.h>
#include <string.h>

#include "book.h"

int main(int argc, char *argv[]) {

    Collection col;
    col.count = 0;

    processFile(argv[1], collAddBook, &col);

    collSortTitle(&col);

    char cmd[10];

    while (1) {

        fgets(cmd, 10, stdin);

        if (cmd[0] == 'l') {

            for (int i = 0; i < col.count; i++) {
                printf("%s;%s;%s;%s\n",
                    col.books[i].title,
                    col.books[i].authors,
                    col.books[i].publisher,
                    col.books[i].isbn);
            }
        }

        if (cmd[0] == 'q') {
            break;
        }
    }

    return 0;
}