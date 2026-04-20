#include <stdio.h>

int linePrintRaw(const char *line, void *context);

int processFile(
    const char *filename,
    int (*action)(const char *line, void *context),
    void *context
);

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Uso: %s <ficheiro>\n", argv[0]);
        return 1;
    }

    int result = processFile(argv[1], linePrintRaw, NULL);

    if (result == -1) {
        printf("Erro ao abrir ficheiro\n");
        return 1;
    }

    return 0;
}
