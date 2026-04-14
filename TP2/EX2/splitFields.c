#include <stdio.h>

int splitFields(char *str, char *fields[], int max){
    int count = 0;

    if (max > 0)
    {
        fields[count] = str;
    }
    count++;
    
    for (int i = 0; str[i] != '\0'; i++)
    {
        if (str[i] == ';')
        {
            str[i] = '\0';
            if (count < max)
            {
                fields[count] = &str[i+1];
            }
            count++;
        }
        
    }
    return count;
}
