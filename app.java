import java.lang.Cloneable;


# K is a key bit string of any length, including zero
# X is the main input bit string. It may be of any length, including zero
# L is an integer representing the requested output length in bits
# S is an optional customization bit string of any length, including zero. If no customization is desired, S is set to the empty string.


KMACXOF256(K, X, L, S):
# Validity Conditions: len(K) < 2^2040 and 0 <= L  and len(S) < 2^2040

	newX = bytepad(encode_string(K), 136) || X || right_encode(0)
	T = bytepad(encode_string("KMAC") || encode_string(S), 136)
	return KECCAK[512](T || newX || 00, L)
	

KMACXOF256(K, X, L, S):
# Validity Conditions: len(K) < 2^2040 and 0 <= L and len(S) < 2^2040

	newX = bytepad(encode_string(K), 136) || X || right_encode(0)
	return cSHAKE256(newX, L, "KMAC", S)
	
cSHAKE256(X, L, N, S):
#Validity Conditions: len(N) < 2^2040 and len(S) < 2^2040

	if N = "" and S = ""
		return SHAKE256(X,L);
	else
		return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L)
	
bytepad(X, w):
# Validity Conditions: w > 0

	z = left-encode(w) || X
	while len(z) mod 8 != 0
		z = z || 0
	while (len(z)/8) mod w != 0
		z = z || 00000000
	return z
	
encode_string(S):
# Validity Conditions: 0 <= len(S) < 2^2040

	return left_encode(len(S)) || S
	
left_encode(x):
# Validity Conditions:  0 <= x < 2^2040

# Let n be the smallest positive integer for which 2 ^ 8n > x
# Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
#	x = (Rheiman Sum) 2^8(n - i) xi, for i = 1 to n
# Let Oi = enc8(xi), i = 1 to n
# Let O0 = enc8(n)
# Return O = O0 || Oi || ... || On-1 || On

right_encode(x):
# Validity Conditions: 0 <= x < 2^2040

# Let n be the smallest positive integer for which 2^8n > x
# Let x1, x2, ..., xn be the base-256 encoding of x satisfying:
#	x = (Rheiman Sum) 2^8(n-1) xi, for i = 1 to n
# Let Oi = enc8(x1), for i = 1 to n
# Let On+1 = enc8(n)
# Return O = O1 || O2 || ... || On || On+1



# KECCAK ?? Import from java.lang.Cloneable
