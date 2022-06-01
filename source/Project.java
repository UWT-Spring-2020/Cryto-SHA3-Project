class Project {
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		// codes
		
		
		
	}
	
	public static void cSHAKE256() {
		/* 
			cSHAKE256(X, L, N, S):
			Validity Conditions: len(N)< 22040 and len(S)< 22040
			1. If N = "" and S = "":
			return SHAKE256(X, L);
			2. Else:
			return KECCAK[512](bytepad(encode_string(N) || encode_string(S), 136) || X || 00, L).
			
		*/
	
		System.out.println("cSHAKE256 is currently being developed. Come back later!");

	}
	
	public static void KMACXOF256(K, X, L, S) {
		/*
			KMACXOF256(K, X, L, S):
			Validity Conditions: len(K) <22040 and 0 ≤ L and len(S) < 22040
			1. newX = bytepad(encode_string(K), 136) || X || right_encode(0).
			2. return cSHAKE256(newX, L, “KMAC”, S).
		*/
		
		System.out.println("KMACXOF256 is currently being developed. Come back later!");
	}
	
	

		
}
