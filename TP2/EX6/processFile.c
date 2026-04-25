#include <stdio.h>
#include "book.h"

#define MAX_LINE 1024

int processFile(const char *filename, int (*action)(const char *, void *), void *context) {
    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Erro ao abrir o ficheiro: %s\n", filename);
        return 0;
    }

    char line[MAX_LINE];
    int lines_processed = 0;

    while (fgets(line, MAX_LINE, file) != NULL) {
        action(line, context);
        lines_processed++;
    }

    fclose(file);
    return lines_processed;
}