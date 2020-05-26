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
	
	function createFuncionario($placa, $cor, $descricao, $ano){
		$stmt = $this->con->prepare("INSERT INTO tbFuncionarios (cargo, atividades, salario, foto) VALUES (?, ?, ?, ?)");
		$stmt->bind_param("sssi", $cargo, $atividades, $salario, $foto);
		if($stmt->execute())
			return true; 
		return false; 
	}

	function getFuncionario(){
		$stmt = $this->con->prepare("SELECT id, cargo, atividades, salario, foto FROM tbFuncionarios");
		$stmt->execute();
		$stmt->bind_result($id, $placa, $cor, $descricao, $ano);
		$Funcionarios = array(); 
	
		while($stmt->fetch()){
			$Funcionario  = array();
			$Funcionario['id'] = $id; 
			$Funcionario['cargo'] = $cargo; 
			$Funcionario['atividades'] = $atividades; 
			$Funcionario['salario'] = $salario; 
			$Funcionario['foto'] = $foto; 
			
			array_push($Funcionarios, $Funcionario); 
		}
		
		return $Funcionarios; 
	}
	
	function updateFuncionario($id, $cargo, $atividades, $salario, $foto){
		$stmt = $this->con->prepare("UPDATE tbFuncionarios SET cargo = ?, atividades = ?, salario = ?, foto = ? WHERE id = ?");
		$stmt->bind_param("sssii", $cargo, $atividades, $salario, $foto, $id);
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