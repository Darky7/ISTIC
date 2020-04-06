#include <stdio.h>
int* p;

int search(int* tab, int element, int nb)
{
  int min = 0, max = nb-1;

  while (min <= max) {
    int mid = (min+max) / 2;
    if (tab[mid] == element) {
      return 1; /* found */
    }
    if (tab[mid] < element) {
      min = mid; 
    }
    else {
      max = mid;
    }
  }
  return 0; /* not found :( */
}


int main()
{
  int x[] = { 1, 5, 8, 9, 12, 16 };

  printf("Result: %i\n", search(p,  1, 6));
  printf("Result: %i\n", search(x,  1, 6)); /* must return  1 */
  printf("Result: %i\n", search(x, 12, 6)); /* must return  1 */
  printf("Result: %i\n", search(x,  3, 6)); /* must return  0 */
  return 1;
}
