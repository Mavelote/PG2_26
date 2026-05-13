#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "book.h"

#define MAX_LINE 1024
#define MAX_FIELDS 15
#define VEC_BLOCK 50

int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);

char* my_strdup(const char *s) {
    if (s == NULL) return NULL;
    char *copy = malloc(strlen(s) + 1);
    if (copy != NULL) {
        strcpy(copy, s);
    }
    return copy;
}

Book *bookCreate(const char *line) {
    char work_line[MAX_LINE];
    char *fields[MAX_FIELDS];

    strncpy(work_line, line, MAX_LINE - 1);
    work_line[MAX_LINE - 1] = '\0';

    int num_fields = splitFields(work_line, fields, MAX_FIELDS);

    if (num_fields < 5) {
        return NULL; 
    }

    Book *new_book = (Book *)malloc(sizeof(Book));
    if (new_book == NULL) {
        return NULL; 
    }

    separatorUnify(fields[0]);
    new_book->title = my_strdup(fields[0]);

    separatorUnify(fields[1]);
    strncpy(new_book->isbn, fields[1], MAX_ISBN - 1);
    new_book->isbn[MAX_ISBN - 1] = '\0';

    separatorUnify(fields[3]);
    new_book->authors = my_strdup(fields[3]);

    separatorUnify(fields[4]);
    new_book->publisher = my_strdup(fields[4]);

    if (new_book->title == NULL || new_book->authors == NULL || new_book->publisher == NULL) {
        bookFree(new_book);
        return NULL;
    }

    return new_book;
}

void bookFree(Book *b) {
    if (b == NULL) return;

    
    if (b->title != NULL) free(b->title);
    if (b->authors != NULL) free(b->authors);
    if (b->publisher != NULL) free(b->publisher);

    
    free(b);
}

VecBookRef *vecRefCreate(void) {
    VecBookRef *vec = (VecBookRef *)malloc(sizeof(VecBookRef));
    if (vec == NULL) return NULL;

    vec->size = 0;
    vec->space = VEC_BLOCK;
    vec->refs = (Book **)malloc(vec->space * sizeof(Book *));

    if (vec->refs == NULL) {
        free(vec); 
        return NULL;
    }

    return vec;
}

void vecRefAdd(VecBookRef *vec, Book *ref) {
    if (vec == NULL || ref == NULL) return;

    
    if (vec->size == vec->space) {
        vec->space += VEC_BLOCK;
        
       
        Book **temp = (Book **)realloc(vec->refs, vec->space * sizeof(Book *));
        if (temp == NULL) {
            printf("Erro de memória ao expandir o vetor.\n");
            return;
        }
        vec->refs = temp;
    }

   
    vec->refs[vec->size] = ref;
    vec->size++;
}

int vecRefSize(VecBookRef *vec) {
    if (vec == NULL) return 0;
    return vec->size;
}

Book *vecRefGet(VecBookRef *vec, int index) {
    if (vec == NULL || index < 0 || index >= vec->size) {
        return NULL;
    }
    return vec->refs[index];
}

int cmpTitle(const void *a, const void *b) {
    
    Book *bookA = *(Book **)a;
    Book *bookB = *(Book **)b;
    return strcmp(bookA->title, bookB->title);
}

void vecRefSortTitle(VecBookRef *vec) {
    if (vec == NULL || vec->size <= 1) return;
    qsort(vec->refs, vec->size, sizeof(Book *), cmpTitle);
}

int cmpIsbn(const void *a, const void *b) {
    Book *bookA = *(Book **)a;
    Book *bookB = *(Book **)b;
    return strcmp(bookA->isbn, bookB->isbn);
}

void vecRefSortIsbn(VecBookRef *vec) {
    if (vec == NULL || vec->size <= 1) return;
    qsort(vec->refs, vec->size, sizeof(Book *), cmpIsbn);
}

int cmpSearchIsbn(const void *key, const void *element) {
    const char *search_isbn = (const char *)key; 
    Book *book = *(Book **)element;              
    return strcmp(search_isbn, book->isbn);
}

Book *vecRefSearchIsbn(VecBookRef *vec, char *isbn) {
    if (vec == NULL || vec->size == 0) return NULL;

    Book **result = (Book **)bsearch(isbn, vec->refs, vec->size, sizeof(Book *), cmpSearchIsbn);
    
    if (result != NULL) {
        return *result; 
    }
    return NULL; 
}

void vecRefFree(VecBookRef *vec, int freeBooks) {
    if (vec == NULL) return;

    if (freeBooks) {
        for (int i = 0; i < vec->size; i++) {
            bookFree(vec->refs[i]); 
        }
    }

    if (vec->refs != NULL) {
        free(vec->refs);
    }
    free(vec);
}

DynCollection *dynCollCreate(void) {
    DynCollection *coll = (DynCollection *)malloc(sizeof(DynCollection));
    if (coll == NULL) return NULL;

    coll->titleVec = vecRefCreate();
    coll->isbnVec = vecRefCreate();

    if (coll->titleVec == NULL || coll->isbnVec == NULL) {
        if (coll->titleVec) vecRefFree(coll->titleVec, 0);
        if (coll->isbnVec) vecRefFree(coll->isbnVec, 0);
        free(coll);
        return NULL;
    }
    return coll;
}

int dynCollAddBook(const char *line, void *context) {
    DynCollection *coll = (DynCollection *)context; 

    
    Book *new_book = bookCreate(line);
    if (new_book == NULL) {
        return 0; 
    }

    
    vecRefAdd(coll->titleVec, new_book);
    vecRefAdd(coll->isbnVec, new_book);

    return 1; 
}

void dynCollFill(DynCollection *coll, const char *filename) {
    if (coll == NULL || filename == NULL) return;

    processFile(filename, dynCollAddBook, coll);

    vecRefSortTitle(coll->titleVec);
    vecRefSortIsbn(coll->isbnVec);
}

void dynCollFree(DynCollection *coll) {
    if (coll == NULL) return;
    vecRefFree(coll->titleVec, 1); 
    vecRefFree(coll->isbnVec, 0);
    free(coll);
}