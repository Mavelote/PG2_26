#include <stdlib.h>
#include "book.h"

/* Comparação de dois ponteiros para livros pelo ISBN */
static int cmpRefIsbn(const void *a, const void *b) {
    BookData * const *pa = a;
    BookData * const *pb = b;

    return strcmp_ic((*pa)->isbn, (*pb)->isbn);
}
/* Comparação entre a chave pesquisada e um livro */
static int cmpKeyIsbn(const void *key, const void *elem) {
    const char *isbn = key;
    BookData * const *book = elem;

    return strcmp_ic(isbn, (*book)->isbn);
}
/* Ordena o array refs por ISBN */
void collSortRefIsbn(Collection *col) {
    int i;

    /* Inicializa refs a apontar para books */
    for (i = 0; i < col->count; i++) {
        col->refs[i] = &col->books[i];
    }

    /* Ordena os ponteiros por ISBN */
    qsort(col->refs,
          col->count,
          sizeof(BookData *),
          cmpRefIsbn);
}
/* Procura um livro pelo ISBN usando bsearch */
BookData *collSearchIsbn(Collection *col, const char *isbn) {
    BookData **result;

    /* Pesquisa binária no array refs */
    result = bsearch(isbn,
                     col->refs,
                     col->count,
                     sizeof(BookData *),
                     cmpKeyIsbn);

    /* ISBN não encontrado */
    if (result == NULL) {
        return NULL;
    }
    /* Retorna o livro encontrado */
    return *result;
}