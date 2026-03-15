#include <stdio.h>

#define MAX_FIELDS 10
#define MAX_LINE 256

int splitFields(char *str, char *fields[], int max);

int main(){
    char linha[MAX_LINE];
    char *fields[MAX_FIELDS];

    while (fgets(linha, MAX_LINE, stdin) != NULL)
    {
        int n_campos = splitFields(linha, fields, MAX_FIELDS);
        for (int i = 0; i < n_campos && i < MAX_FIELDS; i++)
        {
            printf("{%s}", fields[i]);
        }
        
    }
    return 0;
    
}

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