#include <ctype.h>

int strcmp_ic( const char *str1, const char *str2 ){
  while (*str1 != '\0' && *str2 != '\0' ){
    char c1 = tolower(*str1);
    char c2 = tolower(*str2);

    if (c2 != c1) {
        return c2 - c1;
}
    str1++;
    str2++; 
}
    return tolower(*str1) - tolower(*str2);

}