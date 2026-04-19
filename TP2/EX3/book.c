#include <string.h>
#include "book.h"

#define MAX_LINE 1024
#define MAX_FIELDS 15

int fillBookData(BookData *b, const char *line){
    char work_line[MAX_LINE];
    char *fields[MAX_FIELDS];

    strncpy(work_line, line, MAX_LINE-1);
    work_line[MAX_LINE-1] = '\0';

    int num_fields = splitFields(work_line, fields, MAX_FIELDS);

    if(num_fields < 5) {
        return 0;
    }

    strncpy(b->title, fields[0], MAX_TITLE-1);
    b->title[MAX_TITLE-1] = '\0';
    separatorUnify(b->title);

    strncpy(b->isbn, fields[1], SIZE_ISBN - 1);
    b->isbn[SIZE_ISBN - 1] = '\0';
    separatorUnify(b->isbn);

    strncpy(b->authors, fields[3], MAX_AUTHORS - 1);
    b->authors[MAX_AUTHORS - 1] = '\0';
    separatorUnify(b->authors);

    strncpy(b->publisher, fields[4], MAX_PUB_NAME - 1);
    b->publisher[MAX_PUB_NAME - 1] = '\0';
    separatorUnify(b->publisher);

    return 1;
}

int collAddBook(const char *line, void *context) {
    Collection *col = (Collection *)context;

    
    if (col->count >= MAX_BOOKS) {
        return 0; 
    }

   
    if (fillBookData(&(col->books[col->count]), line) == 1) {
        col->count++; 
        return 1;
    }

    return 0; 
}