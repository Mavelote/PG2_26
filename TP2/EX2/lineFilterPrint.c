#include <stdio.h>
#include <string.h>

#define MAX_LINE 512
#define MAX_FIELDS 20

int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);
int strcmp_ic(const char *str1, const char *str2);

int lineFilterPrint(const char *line, void *context) {
    char work_line[MAX_LINE];
    char *fields[MAX_FIELDS];
    char *target = (char *)context;
    int n;

    strcpy(work_line, line);
    n = splitFields(work_line, fields, MAX_FIELDS);

    if (n < 1) {
        return 0;
    }

    separatorUnify(fields[0]);

    if (strcmp_ic(fields[0], target) == 0) {
        printf("%s", line);

        if (strchr(line, '\n') == NULL) {
            putchar('\n');
        }

        return 1;
    }

    return 0;
}