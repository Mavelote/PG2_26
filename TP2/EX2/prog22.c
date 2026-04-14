#include <stdio.h>
#include <string.h>

#define MAX_LINE 512

int processFile(const char *filename,
                int (*action)(const char *line, void *context),
                void *context);

int lineFilterPrint(const char *line, void *context);
void separatorUnify(char str[]);

int main(int argc, char *argv[]) {
    char target[MAX_LINE];
    int shown;

    if (argc != 3) {
        fprintf(stderr, "Uso: %s <ficheiro> <string>\n", argv[0]);
        return 1;
    }

    strcpy(target, argv[2]);
    separatorUnify(target);

    shown = processFile(argv[1], lineFilterPrint, target);

    if (shown < 0) {
        fprintf(stderr, "Erro ao abrir ficheiro: %s\n", argv[1]);
        return 1;
    }

    printf("Linhas apresentadas: %d\n", shown);

    return 0;
}