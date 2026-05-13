#ifndef BOOK_H
#define BOOK_H

#define MAX_ISBN 20


typedef struct {
    char *title;      
    char isbn[MAX_ISBN]; 
    char *authors;    
    char *publisher;  
} Book;


typedef struct {
    Book **refs;     
    int size;        
    int space;        
} VecBookRef;


typedef struct {
    VecBookRef *titleVec;
    VecBookRef *isbnVec;  
} DynCollection;


Book *bookCreate(const char *line);
void bookFree(Book *b);

VecBookRef *vecRefCreate(void);
void vecRefAdd(VecBookRef *vec, Book *ref);
int vecRefSize(VecBookRef *vec);
Book *vecRefGet(VecBookRef *vec, int index);
void vecRefSortTitle(VecBookRef *vec);
void vecRefSortIsbn(VecBookRef *vec);
Book *vecRefSearchIsbn(VecBookRef *vec, char *isbn);
void vecRefFree(VecBookRef *vec, int freeBooks);

DynCollection *dynCollCreate(void);
int dynCollAddBook(const char *line, void *context);
void dynCollFill(DynCollection *coll, const char *filename);
void dynCollFree(DynCollection *coll);

int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);
int processFile(const char *filename, int (*action)(const char *, void *), void *context);

#endif