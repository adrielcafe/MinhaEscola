# Minha Escola
###### Disponível no [Google Play](http://play.google.com/store/apps/details?id=com.adrielcafe.minhaescola).

**Minha Escola** é um aplicativo Android *open source*. Este aplicativo pode ser usado por **qualquer escola** para exibir os horários e histórico escolar do aluno, facilitando a gestão escolar. Isso mesmo, ele é um aplicativo UNIVERSAL! Mas como isso é possível?

As escolas possuem todos os dados dos alunos em seus sistemas de gestão. Se elas disponibilizarem alguns desses dados na web, como os horários e histórico escolar, o aplicativo **Minha Escola** poderá acessar essas informações e exibir no celular.

## Exemplo de Uso
Vamos supor que a *Escola XYZ* forneceu um link único para cada aluno, ele contém os horários e histórico escolar de cada um. Os alunos devem baixar o aplicativo **Minha Escola** no Google Play e informar esse link na tela de configuração. 

Feito isso, o aplicativo irá baixar os horários e histórico escolar do aluno e organiza-los de uma forma muito prática.
Para testar o aplicativo, você pode usar algum desses links de demonstração:

* http://adrielcafe.com/tmp/minhaescola.demo1
* http://adrielcafe.com/tmp/minhaescola.demo2

## Formato dos Dados
Para que o aplicativo Minha Escola consiga ler os dados escolares, eles precisam estar em um formato padronizado. Os dados precisam estar no formato JSON  e a codificação dos caracteres deve ser em UTF-8 . A estrutura do arquivo está descrita abaixo.

*Estou à disposição para tirar dúvidas sobre este processo de integração.*

```javascript
{
    "nomeEscola": "ESCOLA XYZ",
    "nomeEstudante": "ALUNO XYZ",
    "horarios": {
        "Segunda-Feira": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ],
        "Terça-Feira": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ],
        "Quarta-Feira": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ],
        "Quinta-Feira": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ],
        "Sexta-Feira": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ],
        "Sábado": [
            [
                "DISCIPLINA",
                "HORÁRIO"
            ],
            [
                "DISCIPLINA",
                "HORÁRIO"
            ]
        ]
    },
    "historico": {
        "1º Semestre": [
            [
                "DISCIPLINA",
                "NOTA"
            ],
            [
                "DISCIPLINA",
                "NOTA"
            ]
        ],
        "2º Semestre": [
            [
                "DISCIPLINA",
                "NOTA"
            ],
            [
                "DISCIPLINA",
                "NOTA"
            ]
        ],
        "3º Semestre": [
            [
                "DISCIPLINA",
                "NOTA"
            ],
            [
                "DISCIPLINA",
                "NOTA"
            ]
        ],
        "4º Semestre": [
            [
                "DISCIPLINA",
                "NOTA"
            ],
            [
                "DISCIPLINA",
                "NOTA"
            ]
        ]
    }
}
```

![](http://i.imgur.com/IKBZRXb.png)
![](http://i.imgur.com/2o6gQ9Q.png)
![](http://i.imgur.com/AipGHLu.png)
