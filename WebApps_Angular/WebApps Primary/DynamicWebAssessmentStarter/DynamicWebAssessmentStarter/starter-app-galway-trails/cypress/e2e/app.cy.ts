describe('My First Test', () => {
  
  it('Contains headers', () => {
    cy.visit('')
    cy.get('h1').contains('Galway')
  })

  it('Contains footers', () => {
    cy.visit('')
    cy.get('footer').contains('2023')
  })

  // it('Navigates to the main page and verify the initial list of trails', () => {
  //   cy.visit('/')
  //   cy.get('tbody > tr').should('have.length.gt', 2)
  // })

  it('Navigates to the main page and verify the initial list of trails.', () => {
    cy.visit('')
    cy.get('#defaultTrail').select("Show Trails");
    cy.get('#defaultTrail').should('have.value', '')    
  })

  
  it('Clicks a button and verify that the list of trails is displayed in a different order (Time to Complete in Desscending).', () => {
    cy.visit('')
    cy.get('#trailByTimeToComplete').select("Show Trails By Time to Complete Desc");
    cy.get('#trailByTimeToComplete').should('have.value', 'defaultDesc')    
  })
}
)