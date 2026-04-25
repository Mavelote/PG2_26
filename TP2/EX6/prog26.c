#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "book.h"

int compareTitles(const void *a, const void *b) {
    BookData *bookA = *(BookData **)a;
    BookData *bookB = *(BookData **)b;
    return strcmp(bookA->title, bookB->title);
}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Uso: %s <dados.csv>\n", argv[0]);
        return 1;
    }

    Collection my_collection;
    my_collection.count = 0;

    
    processFile(argv[1], collAddBook, &my_collection);

    
    for (int i = 0; i < my_collection.count; i++) {
        my_collection.refs[i] = &my_collection.books[i];
    }
    qsort(my_collection.refs, my_collection.count, sizeof(BookData *), compareTitles);

  
    char cmd_line[256];
    printf("Introduza comando ('a palavra' ou 'sair'):\n> ");
    
    while (fgets(cmd_line, sizeof(cmd_line), stdin) != NULL) {
        char cmd;
        char search_word[100] = "";
        
        
        if (sscanf(cmd_line, "%c %99[^\n]", &cmd, search_word) >= 2 && cmd == 'a') {
            
            
            for (int i = 0; i < my_collection.count; i++) {
                BookData *b = my_collection.refs[i];
                
                if (bookContainsAuthor(b, search_word) == 1) {
                   
                    printf("Titulo: %s | Autor: %s | Editor: %s | ISBN: %s\n", 
                           b->title, b->authors, b->publisher, b->isbn);
                }
            }
        } 
        else if (strncmp(cmd_line, "sair", 4) == 0) {
            break;
        }
        
        printf("> ");
    }

    return 0;
}