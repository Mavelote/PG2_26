    #include <ctype.h>

    int strcmp_ic( const char *str1, const char *str2 ){
    while (*str1 != '\0' && *str2 != '\0' ){
        char c1 = tolower(*str1);
        char c2 = tolower(*str2);

        if (c1 != c2) {
            return c1 - c2;
    }
        str1++;
        str2++; 
    }
        return tolower(*str1) - tolower(*str2);

    }