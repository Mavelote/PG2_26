#include <stdio.h>
#include <string.h>

#include "book.h"

/* Apresenta os dados principais de um livro */
static void printBook(BookData *b) {
    printf("%s;%s;%s;%s\n",
           b->title,
           b->authors,
           b->publisher,
           b->isbn);
}

int main(int argc, char *argv[]) {

    Collection col;
    char cmd;
    char isbn[SIZE_ISBN];
    BookData *book;
    int i;

    /* Verifica os argumentos da linha de comandos */
    if (argc != 2) {
        printf("Uso: %s <ficheiro>\n", argv[0]);
        return 1;
    }

    /* Inicializa a coleção vazia */
    col.count = 0;

    /* Lê os livros do ficheiro */
    processFile(argv[1], collAddBook, &col);

    /* Ordena os livros por título */
    collSortTitle(&col);

    /* Cria e ordena refs por ISBN */
    collSortRefIsbn(&col);

    /* Ciclo principal de comandos */
    while (scanf(" %c", &cmd) == 1) {

        /* Termina o programa */
        if (cmd == 'q') {
            break;
        }

        /* Lista todos os livros */
        if (cmd == 'l') {

            for (i = 0; i < col.count; i++) {
                printBook(&col.books[i]);
            }

        /* Pesquisa um livro pelo ISBN */
        } else if (cmd == 'i') {

            scanf("%s", isbn);

            book = collSearchIsbn(&col, isbn);

            /* Livro não encontrado */
            if (book == NULL) {
                printf("Livro inexistente\n");

            /* Livro encontrado */
            } else {
                printBook(book);
            }
        }
    }

    return 0;
}