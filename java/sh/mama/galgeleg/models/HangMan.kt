package sh.mama.galgeleg.models

class HangMan() {
    private var level = 0

    private val states = listOf<String>(
        """
    _________
    |/        
    |              
    |                
    |                 
    |               
    |                   
    |___                 
""".trimIndent(),
        """
   _________
    |/   |      
    |              
    |                
    |                 
    |               
    |                   
    |___                 
""".trimIndent(),
        """
   _________       
    |/   |              
    |   (_)
    |                         
    |                       
    |                         
    |                          
    |___                       
""".trimIndent(),
        """
   ________               
    |/   |                   
    |   (_)                  
    |    |                     
    |    |                    
    |                           
    |                            
    |___                    
""".trimIndent(),
        """
   _________             
    |/   |               
    |   (_)                   
    |   /|                     
    |    |                    
    |                        
    |                          
    |___                          
""".trimIndent(),
        """
   _________              
    |/   |                     
    |   (_)                     
    |   /|\                    
    |    |                       
    |                             
    |                            
    |___                          
""".trimIndent(),
        """
   ________                   
    |/   |                         
    |   (_)                      
    |   /|\                             
    |    |                          
    |   /                            
    |                                  
    |___                              
""".trimIndent(),
        """
   ________
    |/   |     
    |   (_)    
    |   /|\           
    |    |        
    |   / \        
    |               
    |___           
""".trimIndent()
    )

    fun kill(): Boolean {
        this.level += 1
        return this.states.size-1 > this.level
    }

    fun getState(): String {
        return this.states[this.level]
    }
}
