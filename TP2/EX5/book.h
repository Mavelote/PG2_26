#define BOOK_H

#define MAX_BOOKS 350     
#define MAX_TITLE 160     
#define SIZE_ISBN 20       
#define MAX_AUTHORS 100    
#define MAX_PUB_NAME 64    


typedef struct book {
    char title[MAX_TITLE];
    char isbn[SIZE_ISBN];
    char authors[MAX_AUTHORS];
    char publisher[MAX_PUB_NAME];
} BookData;

typedef struct {
    BookData books[MAX_BOOKS];
    BookData *refs[MAX_BOOKS];
    int count;
} Collection;

int fillBookData(BookData *b, const char *line);
int collAddBook(const char *line, void *context);
int processFile(const char *filename, int (*action)(const char *, void *), void *context);
int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);

// Adiciona estas duas linhas ao book.h
void collSortTitle(Collection *col);
int strcmp_ic(const char *str1, const char *str2);

//EX.5 - Ordena os ponteiros da coleção por ISBN Ordena os ponteiros da coleção por ISBN, procura um livro através ISBN 
void collSortRefIsbn(Collection *col);
BookData *collSearchIsbn(Collection *col, const char *isbn);