#include <stdlib.h>

#include "book.h"

int cmpTitle(const void *a, const void *b) {
    return strcmp_ic(((BookData *)a)->title, ((BookData *)b)->title);
}

void collSortTitle(Collection *col) {
    qsort(col->books, col->count, sizeof(BookData), cmpTitle);
}