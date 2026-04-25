#include <stdio.h>

int processFile(
    const char *filename,
    int (*action)(const char *line, void *context),
    void *context
) {
    FILE *f;
    char line[512];
    int sum = 0;

    f = fopen(filename, "r");
    if (f == NULL) {
        return -1;
    }

    while (fgets(line, sizeof(line), f) != NULL) {
        sum += action(line, context);
    }

    fclose(f);
    return sum;
}
