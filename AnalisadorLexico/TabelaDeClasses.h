typedef struct 
{
    int classeMAX;
    int **matriz;
}_TabelaDeClasses;

typedef _TabelaDeClasses* TabelaDeClasses;

TabelaDeClasses CarregaTabelaDeClasses();

//Retorna -1 se o estado n�o for final, isto � n�o est� na tabela
int ClasseDoEstado(TabelaDeClasses tabClasses, int estado);

