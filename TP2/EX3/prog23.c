#include <stdio.h>
#include "book.h"

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Uso: %s <dados.csv>\n", argv[0]);
        return 1;
    }

    Collection my_collection;
    my_collection.count = 0; 

    processFile(argv[1], collAddBook, &my_collection);

    for (int i = 0; i < my_collection.count; i++) {
        BookData *b = &my_collection.books[i];
        printf("%s;%s;%s;%s\n", b->title, b->isbn, b->authors, b->publisher);
    }

    printf("\nTotal de livros carregados: %d\n", my_collection.count);

    return 0;
}