<?php 

	require_once '../includes/DbOperation.php';

	function isTheseParametersAvailable($params){
	
		$available = true; 
		$missingparams = ""; 
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available = false; 
				$missingparams = $missingparams . ", " . $param; 
			}
		}
		
		
		if(!$available){
			$response = array(); 
			$response['error'] = true; 
			$response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';
			
		
			echo json_encode($response);
			
		
			die();
		}
	}
	
	
	$response = array();
	

	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){
	
			case 'createFuncionarios':
				
				isTheseParametersAvailable(array('cargo','atividades','salario'));
				
				$db = new DbOperation();
				
				$result = $db->createFuncionarios(
					$_POST['cargo'],
					$_POST['atividades'],
					$_POST['salario']
				);
				

			
				if($result){
					
					$response['error'] = false; 

					
					$response['message'] = 'Funcionario adicionado com sucesso';

					
					$response['Funcionarios'] = $db->getFuncionario();
				}else{

					
					$response['error'] = true; 

				
					$response['message'] = 'Erro ao adicionar o funcionario!!!';
				}
				
			break; 
			
		
			case 'getFuncionario':
				$db = new DbOperation();
				$response['error'] = false; 
				$response['message'] = 'Funcionario selecionado com sucesso!!!';
				$response['Funcionarios'] = $db->getFuncionario();
			break; 
			
			
		
			case 'updateFuncionario':
				isTheseParametersAvailable(array('id','cargo','atividades','salario'));
				$db = new DbOperation();
				$result = $db->updateFuncionario(
					$_POST['id'],
					$_POST['cargo'],
					$_POST['atividades'],
					$_POST['salario']
				);
				
				if($result){
					$response['error'] = false; 
					$response['message'] = 'Funcionario alterado com sucesso';
					$response['Funcionarios'] = $db->getFuncionario();
				}else{
					$response['error'] = true; 
					$response['message'] = 'Erro ao alterar!!!';
				}
			break; 
			
			
			case 'deleteFuncionario':

				
				if(isset($_GET['id'])){
					$db = new DbOperation();
					if($db->deleteFuncionario($_GET['id'])){
						$response['error'] = false; 
						$response['message'] = 'Funcionarios excluído com sucesso';
						$response['Funcionarios'] = $db->getFuncionario();
					}else{
						$response['error'] = true; 
						$response['message'] = 'Erro ao excluir!!!';
					}
				}else{
					$response['error'] = true; 
					$response['message'] = 'Favor inserir o id.';
				}
			break; 
		}
		
	}else{
		 
		$response['error'] = true; 
		$response['message'] = 'Chamada de API inválida';
	}
	
	echo json_encode($response);
	
	
