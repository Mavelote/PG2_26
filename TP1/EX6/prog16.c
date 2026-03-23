#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE 256
#define MAX_FIELDS 20

int splitFields(char *str, char *fields[], int max);
void separatorUnify(char str[]);
int strcmp_ic(const char *str1, const char *str2);

int main(int argc, char *argv[]){
    
    if (argc != 3)
    {
        printf("Uso: %s <posicao_do_campo> <palavra_a_pesquisar>\n", argv[0]);
        return 1;   
    }
    
    unsigned long field_pos = strtoul(argv[1], NULL, 10);

    int target_index = field_pos - 1 ;

    char search_string[MAX_LINE];
    strcpy(search_string, argv[2]);
    separatorUnify(search_string);

    char original_line[MAX_LINE];
    char work_line[MAX_LINE];
    char *fields[MAX_FIELDS];

    while (fgets(original_line, MAX_LINE, stdin) != NULL)
    {
        strcpy(work_line, original_line);
        int n_campos = splitFields(work_line, fields, MAX_FIELDS);

        if (target_index >= 0 && target_index < n_campos)
        {
            separatorUnify(fields[target_index]);

            if (strcmp_ic(fields[target_index], search_string) == 0)
            {
                printf("%s", original_line);
            }
            
        }
        
    }
    return 0;
    
}