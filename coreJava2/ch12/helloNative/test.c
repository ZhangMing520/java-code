#include <stdlib.h>
#include <stdio.h>

int main()
{
    char *fmt = "8.2f";
    int width = atoi(fmt);
    printf("%d\n", width);

    char cret[30];
    char *format = "Amount due = %8.2f";
    double price = 44.95;
    double tax = 7.75;
    double amountDue = price * (1 + tax / 100);
    sprintf(cret, format, amountDue);
    printf("%s\n", cret);
    return 0;
}