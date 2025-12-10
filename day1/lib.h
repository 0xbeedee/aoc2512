#ifndef LIB_H
#define LIB_H

#include <stdio.h>

// Struct simulating dial behaviour in an OOP-like manner.
typedef struct dial_t
{
    unsigned int current_value, max_value;
} Dial;

void turn_dial(Dial *self, int rotation_amount, size_t *n_zeros_hit);

size_t count_lines(FILE *fptr);
int *parse_rotation_file(FILE *fptr, size_t *num_rotations);

#endif
