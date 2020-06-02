<?php
 
class DbOperation
{
    
    private $con;
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
         $this->con = $db->connect();
    }
	
	function createFuncionario($cargo, $atividades, $salario){
		$stmt = $this->con->prepare("INSERT INTO tbFuncionarios (cargo, atividades, salario) VALUES (?, ?, ?)");
		$stmt->bind_param("sssi", $cargo, $atividades, $salario);
		if($stmt->execute())
			return true; 
		return false; 
	}

	function getFuncionario(){
		$stmt = $this->con->prepare("SELECT * FROM tbFuncionarios");
		$stmt->execute();
		$stmt->bind_result($id, $cargo, $atividades, $salario);
		$Funcionarios = array(); 
	
		while($stmt->fetch()){
			$Funcionario  = array();
			$Funcionario['id'] = $id; 
			$Funcionario['cargo'] = $cargo; 
			$Funcionario['atividades'] = $atividades; 
			$Funcionario['salario'] = $salario; 
			
			array_push($Funcionarios, $Funcionario); 
		}
		
		return $Funcionarios; 
	}
	
	function updateFuncionario($id, $cargo, $atividades, $salario){
		$stmt = $this->con->prepare("UPDATE tbFuncionarios SET cargo = ?, atividades = ?, salario = ? WHERE id = ?");
		$stmt->bind_param("sssii", $cargo, $atividades, $salario, $id);
		if($stmt->execute())
			return true; 
		return false; 
	}
	
	
	function deleteFuncionario($id){
		$stmt = $this->con->prepare("DELETE FROM tbFuncionarios WHERE id = ? ");
		$stmt->bind_param("i", $id);
		if($stmt->execute())
			return true; 
		
		return false; 
	}
}